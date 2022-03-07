import { TestBed } from '@angular/core/testing';

import { TeleService } from './tele.service';

describe('TeleService', () => {
  let service: TeleService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TeleService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
