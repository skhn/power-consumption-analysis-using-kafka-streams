import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { AvgWattsComponent } from './avg-watts/avg-watts.component';
import { SubComponent } from './sub/sub.component';
import { SubDevicesComponent } from './sub-devices/sub-devices.component';

@NgModule({
  declarations: [
    AppComponent,
    AvgWattsComponent,
    SubComponent,
    SubDevicesComponent,
  ],
  imports: [
    BrowserModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
