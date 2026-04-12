import { Component, OnInit } from '@angular/core';
import { AppUser } from '../../core/models/app-user.model';
import { CouponValidationResponse } from '../../core/models/coupon.model';
import { FoodOrder } from '../../core/models/food-order.model';
import { CartService } from '../../core/services/cart.service';
import { OrderApiService } from '../../core/services/order-api.service';
import { UserApiService } from '../../core/services/user-api.service';

@Component({
  selector: 'app-checkout-page',
  templateUrl: './checkout-page.component.html',
  styleUrls: ['./checkout-page.component.css']
})
export class CheckoutPageComponent implements OnInit {
  form = {
    fullName: '',
    address: '',
    phone: '',
    note: '',
    payment: 'cash'
  };

  users: AppUser[] = [];
  selectedUserId: number | null = null;
  isSubmitting = false;
  isApplyingCoupon = false;
  message = '';
  couponCode = '';
  couponMessage = '';
  appliedCoupon: CouponValidationResponse | null = null;
  availableCoupons: string[] = [];

  constructor(
    private readonly cartService: CartService,
    private readonly orderApiService: OrderApiService,
    private readonly userApiService: UserApiService
  ) {}

  ngOnInit(): void {
    this.userApiService.getAll().subscribe({
      next: (users) => {
        this.users = users;
        this.selectedUserId = users.length ? users[0].id ?? null : null;
      },
      error: () => {
        this.message = 'Unable to load users. Create a user first from Admin > Users.';
      }
    });

    this.orderApiService.getCoupons().subscribe({
      next: (coupons) => {
        this.availableCoupons = coupons.filter((coupon) => coupon.active).map((coupon) => coupon.code);
      },
      error: () => {
        this.availableCoupons = [];
      }
    });
  }

  useCoupon(code: string): void {
    this.couponCode = code;
    this.applyCoupon();
  }

  submit(): void {
    this.message = '';

    if (this.selectedUserId === null) {
      this.message = 'Please create/select a user before checkout.';
      return;
    }

    const cartItems = this.cartService.getItems();
    if (!cartItems.length) {
      this.message = 'Your cart is empty.';
      return;
    }

    const orderPayload: FoodOrder = {
      userId: this.selectedUserId,
      restaurantName: cartItems[0].item.name,
      status: 'NEW',
      totalPrice: this.baseTotal,
      couponCode: this.couponCode.trim() ? this.couponCode.trim().toUpperCase() : null,
      discountAmount: 0
    };

    this.isSubmitting = true;
    this.orderApiService.create(orderPayload).subscribe({
      next: () => {
        this.cartService.clear();
        this.appliedCoupon = null;
        this.couponCode = '';
        this.couponMessage = '';
        this.isSubmitting = false;
        this.message = 'Order saved successfully.';
      },
      error: () => {
        this.isSubmitting = false;
        this.message = 'Checkout failed. Ensure selected user exists in backend.';
      }
    });
  }

  applyCoupon(): void {
    this.couponMessage = '';

    if (!this.couponCode.trim()) {
      this.couponMessage = 'Enter a coupon code first.';
      return;
    }

    if (this.selectedUserId === null) {
      this.couponMessage = 'Select a user before applying coupon.';
      return;
    }

    const cartTotal = this.baseTotal;
    if (!cartTotal) {
      this.couponMessage = 'Add items to cart before applying coupon.';
      return;
    }

    this.isApplyingCoupon = true;
    this.orderApiService
      .applyCoupon({
        code: this.couponCode.trim().toUpperCase(),
        userId: this.selectedUserId,
        cartTotal
      })
      .subscribe({
        next: (response) => {
          this.appliedCoupon = response;
          this.couponCode = response.code;
          this.couponMessage = `Coupon applied: -${response.discountAmount.toFixed(2)} TND`;
          this.isApplyingCoupon = false;
        },
        error: (err) => {
          this.appliedCoupon = null;
          this.isApplyingCoupon = false;
          this.couponMessage = err?.error?.error ?? 'Coupon is not valid.';
        }
      });
  }

  clearCoupon(): void {
    this.appliedCoupon = null;
    this.couponCode = '';
    this.couponMessage = 'Coupon removed.';
  }

  get baseTotal(): number {
    return this.cartService.getTotal();
  }

  get discountAmount(): number {
    return this.appliedCoupon?.discountAmount ?? 0;
  }

  get finalTotal(): number {
    return this.appliedCoupon?.finalTotal ?? this.baseTotal;
  }
}
