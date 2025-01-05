import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Hotel } from '../../model/hotel.model';
import { HotelserviceService } from '../../service/hotelservice.service';
import { LocationService } from '../../service/location.service';
import { Router } from '@angular/router';
import { Location } from '../../model/location.model';

@Component({
  selector: 'app-addhotel',
  templateUrl: './addhotel.component.html',
  styleUrl: './addhotel.component.css'
})
export class AddhotelComponent implements OnInit {

  hotel: Hotel = new Hotel();
  locations: Location[] = [];
  formGroup!: FormGroup;
  image: File | null = null;

  constructor(
    private hotelService: HotelserviceService,
    private locationService: LocationService,
    private router: Router,
    private formBuilder: FormBuilder,
  ) { }


  ngOnInit(): void {

    this.loadLocation();


     this.formGroup = this.formBuilder.group({
          name: ['', Validators.required],
          address: ['', Validators.required],
          maximumPrice: ['', Validators.required],
          minimumPrice: ['', Validators.required],
          rating: ['', Validators.required],
          location: [null, Validators.required],
        });
    

  }


  // File selection method
  onFileSelected(evt: Event): void {
    const input = evt.target as HTMLInputElement; // Ensure correct type
    if (input?.files && input.files[0]) {
      this.image = input.files[0];
    }
  }

  loadLocation() {

    this.locationService.getAllLocation().subscribe({

      next: res => {
        this.locations = res;
      },
      error: error => {
        console.log(error);
      }

    });

  }




  onSubmit() {
  
      if (this.formGroup.invalid) {
        alert("Please fill all the required fields with valid data");
      }
  
      if (this.image) {
  
        const hotel: Hotel = {
          ...this.formGroup.value,
          location: {id: this.formGroup.value.location} as Location
        };
  
        this.hotelService.createHotel(hotel, this.image).subscribe({
  
          next: res => {
  
            console.log('Hotel Added Successfully', hotel);
            this.router.navigate(['viewlallhotel']);
  
          },
  
          error: err => {
            console.log('Hotel Added Successfully', hotel);
            console.log('Error adding Hotel');
  
          }
  
        });
      }
      else {
        alert('Pls Select an Image');
      }
  
    }



}
