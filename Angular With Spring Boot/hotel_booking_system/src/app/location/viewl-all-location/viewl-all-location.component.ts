import { Component, OnInit } from '@angular/core';
import { LocationService } from '../../service/location.service';
import { error } from 'console';

@Component({
  selector: 'app-viewl-all-location',
  templateUrl: './viewl-all-location.component.html',
  styleUrl: './viewl-all-location.component.css'
})

export class ViewlAllLocationComponent implements OnInit{

  locations:any;

  constructor(
    private locationService: LocationService,
  ){     }

  ngOnInit(): void {
    
    this.loadLocation();
  }


  loadLocation(){

    this.locationService.getAllLocation().subscribe({

      next: res=> {
        this.locations=res;
      },
      error: error=>{
        console.log(error);
      }

    });

  }





}
