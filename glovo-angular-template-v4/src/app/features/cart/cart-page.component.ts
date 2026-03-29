import { Component } from '@angular/core';
import { CartService } from '../../core/services/cart.service';

@Component({
  selector: 'app-cart-page',
  templateUrl: './cart-page.component.html',
  styleUrls: ['./cart-page.component.css']
})
export class CartPageComponent {
  constructor(public readonly cartService: CartService) {}
}
