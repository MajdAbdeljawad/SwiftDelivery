export interface CouponValidationRequest {
  code: string;
  userId: number;
  cartTotal: number;
}

export interface CouponValidationResponse {
  valid: boolean;
  code: string;
  message: string;
  discountAmount: number;
  finalTotal: number;
}

export interface CouponConsumeRequest {
  code: string;
  userId: number;
}
