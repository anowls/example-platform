package org.zongzi.platform.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
public class ApiResponse<TT> implements Serializable {

	private static final long serialVersionUID = 8835192984096920220L;

	private String level;

	private String msg;

	private TT data;

	public static <TT> ApiResponse<TT> success(String msg, TT data) {
		return new ApiResponse<>("success", msg, data);
	}

	public static ApiResponse<?> success(String msg) {
		return success(msg, null);
	}

	public static <TT> ApiResponse<TT> success(TT data) {
		return success(null, data);
	}

	public static <TT> ApiResponse<TT> info(String msg, TT data) {
		return new ApiResponse<>("info", msg, data);
	}

	public static ApiResponse<?> info(String msg) {
		return success(msg, null);
	}

	public static <TT> ApiResponse<TT> warning(String msg, TT data) {
		return new ApiResponse<>("warning", msg, data);
	}

	public static ApiResponse<?> warning(String msg) {
		return success(msg, null);
	}

	public static <TT> ApiResponse<TT> error(String msg, TT data) {
		return new ApiResponse<>("danger", msg, data);
	}

	public static ApiResponse<?> error(String msg) {
		return success(msg, null);
	}
}
