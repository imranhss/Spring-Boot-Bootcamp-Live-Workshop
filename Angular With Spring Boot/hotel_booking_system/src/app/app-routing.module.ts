import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ViewallhotelComponent } from './hotel/viewallhotel/viewallhotel.component';
import { AddlocationComponent } from './location/addlocation/addlocation.component';
import { ViewlAllLocationComponent } from './location/viewl-all-location/viewl-all-location.component';

const routes: Routes = [

{path: 'viewlallhotel', component: ViewallhotelComponent},
{path: 'addlocation', component: AddlocationComponent},
{path: 'viewlocation', component: ViewlAllLocationComponent},

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }