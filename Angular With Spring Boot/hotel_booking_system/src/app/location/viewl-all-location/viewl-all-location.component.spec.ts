import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewlAllLocationComponent } from './viewl-all-location.component';

describe('ViewlAllLocationComponent', () => {
  let component: ViewlAllLocationComponent;
  let fixture: ComponentFixture<ViewlAllLocationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ViewlAllLocationComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ViewlAllLocationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
