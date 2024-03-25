package com.cardpaymentsystem.tokenservice.controller.v1;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cardpaymentsystem.tokenservice.controller.dto.CardDto;
import com.cardpaymentsystem.tokenservice.controller.dto.CardRequestDto;
import com.cardpaymentsystem.tokenservice.controller.dto.CardResponseDto;
import com.cardpaymentsystem.tokenservice.controller.dto.TokenRequestDto;
import com.cardpaymentsystem.tokenservice.controller.dto.TokenResponseDto;
import com.cardpaymentsystem.tokenservice.controller.dto.TokenValidationDto;
import com.cardpaymentsystem.tokenservice.service.CardService;
import com.cardpaymentsystem.tokenservice.service.TokenService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Token Service")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/token")
public class TokenController {

	private final TokenService tokenService;
	private final CardService cardService;

	@Operation(summary = "Register Card")
	@Parameter(name = "cardNumber", required = true, description = "card number")
	@Parameter(name = "expirationYearMonth", required = true, description = "card expiration year/month (MMYY)")
	@Parameter(name = "userCi", required = true, description = "user CI")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "OK",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = CardResponseDto.class))),
		@ApiResponse(responseCode = "400", description = "Bad Request",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))),
		@ApiResponse(responseCode = "500", description = "Internal Server Error",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class)))
	})
	@PostMapping("/card")
	public ResponseEntity<?> registerCard(@RequestBody CardRequestDto cardRequestDto) throws Exception {

		return ResponseEntity.status(HttpStatus.OK).body(cardService.registerCard(cardRequestDto));
	}

	@Operation(summary = "Search Card")
	@Parameter(name = "cardRefId", required = true, description = "card reference ID")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "OK",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = CardDto.class))),
		@ApiResponse(responseCode = "400", description = "Bad Request",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))),
		@ApiResponse(responseCode = "404", description = "Not Found",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))),
		@ApiResponse(responseCode = "500", description = "Internal Server Error",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class)))
	})
	@GetMapping("/card")
	public ResponseEntity<?> searchCard(@RequestParam String cardRefId) throws Exception {

		return ResponseEntity.status(HttpStatus.OK).body(cardService.searchCard(cardRefId));
	}

	@Operation(summary = "Create Token")
	@Parameter(name = "cardRefId", required = true, description = "card reference ID")
	@Parameter(name = "userCi", required = true, description = "user CI")
	@Parameter(name = "tokenIv", required = true, description = "randomly generated IV for encrypt token")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "OK",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = TokenResponseDto.class))),
		@ApiResponse(responseCode = "400", description = "Bad Request",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))),
		@ApiResponse(responseCode = "500", description = "Internal Server Error",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class)))
	})
	@PostMapping("")
	public ResponseEntity<?> createToken(@RequestBody TokenRequestDto tokenRequestDto) throws Exception {

		return ResponseEntity.status(HttpStatus.OK).body(tokenService.createToken(tokenRequestDto));
	}

	@Operation(summary = "Verify Token")
	@Parameter(name = "token", required = true, description = "token")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "OK",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = TokenValidationDto.class))),
		@ApiResponse(responseCode = "400", description = "Bad Request",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))),
		@ApiResponse(responseCode = "500", description = "Internal Server Error",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class)))
	})
	@GetMapping("/verify")
	public ResponseEntity<?> verifyToken(@RequestParam String token) throws Exception {

		return ResponseEntity.status(HttpStatus.OK).body(tokenService.verifyToken(token));
	}

	@Operation(summary = "Delete Token")
	@Parameter(name = "token", required = true, description = "token")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "OK",
			content = @Content(mediaType = "application/json")),
		@ApiResponse(responseCode = "400", description = "Bad Request",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))),
		@ApiResponse(responseCode = "500", description = "Internal Server Error",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class)))
	})
	@DeleteMapping("/{token}")
	public ResponseEntity<?> deleteToken(@PathVariable String token) throws Exception {

		tokenService.deleteToken(token);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
}
