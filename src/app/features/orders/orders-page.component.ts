import { Component, OnInit } from '@angular/core';
import { FoodOrder } from '../../core/models/food-order.model';
import { OrderApiService } from '../../core/services/order-api.service';

@Component({
  selector: 'app-orders-page',
  templateUrl: './orders-page.component.html',
  styleUrls: ['./orders-page.component.css']
})
export class OrdersPageComponent implements OnInit {
  orders: FoodOrder[] = [];
  isLoading = false;
  errorMessage = '';

  constructor(private readonly orderApiService: OrderApiService) {}

  ngOnInit(): void {
    this.loadOrders();
  }

  loadOrders(): void {
    this.isLoading = true;
    this.errorMessage = '';
    this.orderApiService.getAll().subscribe({
      next: (orders) => {
        this.orders = orders;
        this.isLoading = false;
      },
      error: () => {
        this.errorMessage = 'Unable to load backend orders.';
        this.isLoading = false;
      }
    });
  }
}
