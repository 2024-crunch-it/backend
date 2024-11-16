package com.crunchit.housing_subscription.api;

import com.crunchit.housing_subscription.dto.response.UserSubscriptionLikeResponseDto;
import com.crunchit.housing_subscription.service.UserSubscriptionLikeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/likes")
@RequiredArgsConstructor
public class UserSubscriptionLikeController {

    private final UserSubscriptionLikeService likeService;

    @Operation(summary = "청약 정보 찜 추가", description = "사용자의 관심있는 청약 찜 목록 추가")
    @PostMapping
    public ResponseEntity<?> addLike(@Parameter(description = "사용자 ID") @RequestParam Long userId,
                                     @Parameter(description = "공고 번호") @RequestParam String pblancNo,
                                     @Parameter(description = "주택 관리 번호") @RequestParam String houseManageNo) {
        likeService.addLike(userId, pblancNo, houseManageNo);
        return new ResponseEntity<>("찜 추가 ok", HttpStatus.OK);
    }

    @Operation(summary = "청약 정보 찜 제거", description = "사용자의 특정 청약 찜 목록 재거")
    @DeleteMapping
    public void removeLike(@Parameter(description = "사용자 ID") @RequestParam Long userId,
                           @Parameter(description = "공고 번호") @RequestParam String pblancNo,
                           @Parameter(description = "주택 관리 번호") @RequestParam String houseManageNo) {
        likeService.removeLike(userId, pblancNo, houseManageNo);
    }

    @Operation(summary = "내가 찜한 전체 목록 조회", description = "사용자의 관심있는 청약 찜 목록 전체 조회")
    @GetMapping("/user")
    public List<UserSubscriptionLikeResponseDto> getAllLikesByUser(@Parameter(description = "사용자 ID") @RequestParam Long userId) {
        return likeService.getAllLikesByUser(userId);
    }

}