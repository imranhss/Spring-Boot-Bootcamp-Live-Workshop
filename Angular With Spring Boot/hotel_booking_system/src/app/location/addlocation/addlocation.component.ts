import { Component, OnInit } from '@angular/core';
import { Location } from '../../model/location.model';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { LocationService } from '../../service/location.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-addlocation',
  templateUrl: './addlocation.component.html',
  styleUrl: './addlocation.component.css'
})
export class AddlocationComponent implements OnInit {

  location: Location = new Location();
  formGroup!: FormGroup;
  image: File | null = null;

  constructor(
    private locationService: LocationService,
    private formBuilder: FormBuilder,
    private router: Router
  ) { }

  ngOnInit(): void {
    // Initialize the reactive form
    this.formGroup = this.formBuilder.group({
      name: ['', Validators.required]
    });
  }

  // File selection method
  onFileSelected(evt: Event): void {
    const input = evt.target as HTMLInputElement; // Ensure correct type
    if (input?.files && input.files[0]) {
      this.image = input.files[0];
    }
  }

  onSubmit() {

    if (this.formGroup.invalid) {
      alert("Please fill all the required fields with valid data");
    }

    if (this.image) {

      const location: Location = {
        ...this.formGroup.value
      };

      this.locationService.createLocation(location, this.image).subscribe({

        next: res => {

          console.log('Location Added Successfully', location);
          this.router.navigate(['viewlallhotel']);

        },

        error: err => {
          console.log('Location Added Successfully', location);
          console.log('Error adding Location');

        }

      });
    }
    else {
      alert('Pls Select an Image');
    }

  }




}
