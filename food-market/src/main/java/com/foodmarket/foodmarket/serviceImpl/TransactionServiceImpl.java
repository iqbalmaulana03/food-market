package com.foodmarket.foodmarket.serviceImpl;

import com.foodmarket.foodmarket.dto.TransactionDTO;
import com.foodmarket.foodmarket.dto.response.ListTransaction;
import com.foodmarket.foodmarket.dto.response.TransactionResponse;
import com.foodmarket.foodmarket.entity.Food;
import com.foodmarket.foodmarket.entity.Transaction;
import com.foodmarket.foodmarket.entity.User;
import com.foodmarket.foodmarket.exception.ResourceNotFoundException;
import com.foodmarket.foodmarket.mapper.AutoTransactionMapper;
import com.foodmarket.foodmarket.repository.FoodRepository;
import com.foodmarket.foodmarket.repository.TransactionRepository;
import com.foodmarket.foodmarket.service.TransactionService;
import com.midtrans.Config;
import com.midtrans.ConfigFactory;
import com.midtrans.httpclient.error.MidtransError;
import com.midtrans.service.MidtransSnapApi;
import lombok.AllArgsConstructor;
import org.json.JSONArray;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private TransactionRepository transactionRepository;
    private FoodRepository foodRepository;

    @Override
    public TransactionResponse checkOut(TransactionDTO transactionDTO) throws MidtransError {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = new User();

        if (authentication != null && authentication.getPrincipal() instanceof User loggedUserId){
            user.setId(loggedUserId.getId());
        }

        Food food = foodRepository.findById(transactionDTO.getFoodId()).orElseThrow(
                () -> new ResourceNotFoundException("Food", "foodId", transactionDTO.getFoodId())
        );

            Transaction transaction = new Transaction();

            var count = food.getPrice() * transactionDTO.getQuantity();

            transaction.setFood(food);
            transaction.setQuantity(transactionDTO.getQuantity());
            transaction.setTotal(count);
            transaction.setCreatedAt(new Date());
            transaction.setUser(user);
            transaction.setStatus("pending");

            var save = transactionRepository.save(transaction);

            var payments = getPayment(save, food);

            save.setPaymentURL(payments);

            var newSaved = transactionRepository.save(save);

            TransactionResponse response = AutoTransactionMapper.MAPPER.mapToTransactionDto(newSaved);
            response.setFoodId(transaction.getFood().getId());
            response.setUserId(transaction.getUser().getId());
            response.setPaymentURL(newSaved.getPaymentURL());

            return response;
    }

    @Override
    public ListTransaction getAll() {
        List<Object[]> limit = transactionRepository.getTransactionLimit();
        List<TransactionResponse> list = new ArrayList<>();

        for (Object[] transaction : limit) {
            TransactionResponse responses = new TransactionResponse();
            responses.setId((Long) transaction[0]);
            responses.setFoodId((Long) transaction[1]);
            responses.setUserId((Long) transaction[2]);
            responses.setQuantity((Integer) transaction[3]);
            responses.setTotal((Integer) transaction[4]);
            responses.setStatus((String) transaction[5]);
            list.add(responses);
        }

        ListTransaction response = new ListTransaction();
        response.setResponse(list);
        return response;
    }

    @Override
    public ListTransaction getByID(Long transactionId) {
        List<Object[]> limit = transactionRepository.transactionById(transactionId);
        List<TransactionResponse> list = new ArrayList<>();

        for (Object[] transaction : limit) {
            TransactionResponse responses = new TransactionResponse();
            responses.setId((Long) transaction[0]);
            responses.setFoodId((Long) transaction[1]);
            responses.setUserId((Long) transaction[2]);
            responses.setQuantity((Integer) transaction[3]);
            responses.setTotal((Integer) transaction[4]);
            responses.setStatus((String) transaction[5]);
            responses.setPaymentURL((String) transaction[6]);
            list.add(responses);
        }

        ListTransaction response = new ListTransaction();
        response.setResponse(list);
        return response;
    }

    private String getPayment(Transaction transaction, Food food) throws MidtransError {

        Config snapConfigOptions = Config.builder()
                .setServerKey("")
                .setClientKey("")
                .setIsProduction(false)
                .build();

        MidtransSnapApi snapApi = new ConfigFactory(snapConfigOptions).getSnapApi();

        Map<String, Object> params = new HashMap<>();

        Map<String, Object> transactionDetails = new HashMap<>();
        transactionDetails.put("order_id", transaction.getId().toString());
        transactionDetails.put("gross_amount", transaction.getTotal().longValue());

        Map<String, Object> customerDetails = new HashMap<>();
        customerDetails.put("first_name", transaction.getUser().getName());
        customerDetails.put("email", transaction.getUser().getEmail());

        Map<String, Object> itemDetail = new HashMap<>();
        itemDetail.put("id", food.getId());
        itemDetail.put("price", food.getPrice());
        itemDetail.put("quantity", transaction.getQuantity());
        itemDetail.put("name", food.getName());

        JSONArray itemDetails = new JSONArray();
        itemDetails.put(itemDetail);

        params.put("transaction_details", transactionDetails);
        params.put("item_details", itemDetails);
        params.put("customer_details", customerDetails);

        return snapApi.createTransactionRedirectUrl(params);
    }
}
