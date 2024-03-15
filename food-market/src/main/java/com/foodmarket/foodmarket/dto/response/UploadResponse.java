package com.foodmarket.foodmarket.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadResponse {

    @Schema(description = "avatar_file_name")
    private String avatarFileName;
}
