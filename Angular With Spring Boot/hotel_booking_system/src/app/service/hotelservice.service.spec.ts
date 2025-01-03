import { TestBed } from '@angular/core/testing';

import { HotelserviceService } from './hotelservice.service';

describe('HotelserviceService', () => {
  let service: HotelserviceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(HotelserviceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
