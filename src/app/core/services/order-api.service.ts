import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { FoodOrder } from '../models/food-order.model';
import { CouponConsumeRequest, CouponValidationRequest, CouponValidationResponse } from '../models/coupon.model';

interface CouponSummary {
  id: number;
  code: string;
  active: boolean;
}

@Injectable({
  providedIn: 'root'
})
export class OrderApiService {
  private readonly baseUrl = '/api/orders';
  private readonly couponUrl = '/api/coupons';

  constructor(private readonly http: HttpClient) {}

  getAll(): Observable<FoodOrder[]> {
    return this.http.get<FoodOrder[]>(this.baseUrl);
  }

  create(order: FoodOrder): Observable<FoodOrder> {
    return this.http.post<FoodOrder>(this.baseUrl, order);
  }

  update(id: number, order: FoodOrder): Observable<FoodOrder> {
    return this.http.put<FoodOrder>(`${this.baseUrl}/${id}`, order);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }

  applyCoupon(payload: CouponValidationRequest): Observable<CouponValidationResponse> {
    return this.http.post<CouponValidationResponse>(`${this.couponUrl}/apply`, payload);
  }

  getCoupons(): Observable<CouponSummary[]> {
    return this.http.get<CouponSummary[]>(this.couponUrl);
  }

  consumeCoupon(payload: CouponConsumeRequest): Observable<void> {
    return this.http.post<void>(`${this.couponUrl}/consume`, payload);
  }
}
