import { NgModule } from '@angular/core';
import { BrowserModule, provideClientHydration } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ViewallhotelComponent } from './hotel/viewallhotel/viewallhotel.component';
import { AddhotelComponent } from './hotel/addhotel/addhotel.component';
import { AddlocationComponent } from './location/addlocation/addlocation.component';
import { ViewlAllLocationComponent } from './location/viewl-all-location/viewl-all-location.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { provideHttpClient, withFetch } from '@angular/common/http';

@NgModule({
  declarations: [
    AppComponent,
    ViewallhotelComponent,
    AddhotelComponent,
    AddlocationComponent,
    ViewlAllLocationComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [
    provideClientHydration(),
    provideHttpClient(
      withFetch()
    )
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
