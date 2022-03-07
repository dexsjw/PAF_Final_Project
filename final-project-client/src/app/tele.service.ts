import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Product } from './product-model';
import { lastValueFrom } from 'rxjs';

@Injectable()
export class TeleService {

  constructor(private http: HttpClient) { }

  postProduct(product: Product) {
    return lastValueFrom(
      this.http.post("/api/tele", product)
    );
  }

  getProduct() {
    return lastValueFrom(
      this.http.get("/api/tele/values")
    );
  }
  
}
