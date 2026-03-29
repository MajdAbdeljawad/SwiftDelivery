import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { FoodOrder } from '../models/food-order.model';

@Injectable({
  providedIn: 'root'
})
export class OrderApiService {
  private readonly baseUrl = '/api/orders';

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
}
