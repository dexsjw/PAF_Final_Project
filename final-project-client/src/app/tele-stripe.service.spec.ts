import { TestBed } from '@angular/core/testing';

import { TeleStripeService } from './tele-stripe.service';

describe('TeleStripeService', () => {
  let service: TeleStripeService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TeleStripeService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
