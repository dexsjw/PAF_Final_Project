import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { lastValueFrom } from 'rxjs';
import { Product, Status } from './product-model';

@Injectable()
export class TeleService {

  constructor(private http: HttpClient) { }

  postProduct(product: Product) {
    return lastValueFrom(
      this.http.post("/api/tele", product)
    );
  }

  getStatus(): Promise<Status[]> {
    return lastValueFrom(
      this.http.get<Status[]>("/api/tele/values")
    );
  }
  
}
