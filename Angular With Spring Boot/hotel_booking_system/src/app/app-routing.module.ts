import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ViewallhotelComponent } from './hotel/viewallhotel/viewallhotel.component';
import { AddlocationComponent } from './location/addlocation/addlocation.component';

const routes: Routes = [

{path: 'viewlallhotel', component: ViewallhotelComponent},
{path: 'addlocation', component: AddlocationComponent},

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
