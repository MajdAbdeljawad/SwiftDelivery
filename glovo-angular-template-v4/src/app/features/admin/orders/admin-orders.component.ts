import { Component, OnInit } from '@angular/core';
import { FoodOrder } from '../../../core/models/food-order.model';
import { OrderApiService } from '../../../core/services/order-api.service';

@Component({
  selector: 'app-admin-orders',
  templateUrl: './admin-orders.component.html',
  styleUrls: ['./admin-orders.component.css']
})
export class AdminOrdersComponent implements OnInit {
  orders: FoodOrder[] = [];
  isLoading = false;
  errorMessage = '';

  formOrder: FoodOrder = {
    userId: 1,
    restaurantName: '',
    status: 'NEW',
    totalPrice: 0
  };

  editId: number | null = null;

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
        this.errorMessage = 'Impossible de charger les commandes.';
        this.isLoading = false;
      }
    });
  }

  edit(order: FoodOrder): void {
    this.editId = order.id ?? null;
    this.formOrder = {
      userId: order.userId,
      restaurantName: order.restaurantName,
      status: order.status,
      totalPrice: order.totalPrice
    };
  }

  resetForm(): void {
    this.editId = null;
    this.formOrder = {
      userId: 1,
      restaurantName: '',
      status: 'NEW',
      totalPrice: 0
    };
  }

  save(): void {
    this.errorMessage = '';

    if (this.editId === null) {
      this.orderApiService.create(this.formOrder).subscribe({
        next: () => {
          this.resetForm();
          this.loadOrders();
        },
        error: () => {
          this.errorMessage = 'Creation commande echouee.';
        }
      });
      return;
    }

    this.orderApiService.update(this.editId, this.formOrder).subscribe({
      next: () => {
        this.resetForm();
        this.loadOrders();
      },
      error: () => {
        this.errorMessage = 'Mise a jour commande echouee.';
      }
    });
  }

  remove(id: number | undefined): void {
    if (!id) {
      return;
    }

    this.errorMessage = '';
    this.orderApiService.delete(id).subscribe({
      next: () => this.loadOrders(),
      error: () => {
        this.errorMessage = 'Suppression commande echouee.';
      }
    });
  }
}
