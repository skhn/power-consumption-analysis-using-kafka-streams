import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { AvgWattsComponent } from './avg-watts/avg-watts.component'
import { SubComponent } from './sub/sub.component'
import { SubDevicesComponent} from './sub-devices/sub-devices.component'
import { ErrorPageComponent } from './error-page/error-page.component';


const appRoutes: Routes = [
    { path: 'watts', component: AvgWattsComponent },
    { path: 'sub', component: SubComponent },
    { path: 'devices', component: SubDevicesComponent },
    { path: 'not-found', component: ErrorPageComponent, data: {message: 'Couldn\'t find what you were looking for :('} },
    { path: '**', redirectTo: '/not-found' }
];

@NgModule({
  imports: [
    RouterModule.forRoot(appRoutes)
  ],
  exports: [RouterModule]
})
export class AppRoutingModule {

}
