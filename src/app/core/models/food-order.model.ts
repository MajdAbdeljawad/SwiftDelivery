export interface FoodOrder {
  id?: number;
  userId: number;
  restaurantName: string;
  status: string;
  totalPrice: number;
  couponCode?: string | null;
  discountAmount?: number;
  createdAt?: string;
}
