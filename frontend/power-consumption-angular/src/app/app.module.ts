import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';

import { AppComponent } from './app.component';
import { AvgWattsComponent } from './avg-watts/avg-watts.component';
import { SubComponent } from './sub/sub.component';
import { SubDevicesComponent } from './sub-devices/sub-devices.component';
import { ErrorPageComponent } from './error-page/error-page.component';
import { AvgWattsService } from './avg-watts/avg-watts-service';
import { AppPersistenceService } from './app-persistence.service';
import { AvgWattsChartComponent } from './avg-watts/avg-watts-chart/avg-watts-chart.component';
import { SubDeviceService } from './sub-devices/sub-devices.service';
import { SubDeviceChartComponent } from './sub-devices/sub-device-chart/sub-device-chart.component';

@NgModule({
  declarations: [
    AppComponent,
    AvgWattsComponent,
    SubComponent,
    SubDevicesComponent,
    ErrorPageComponent,
    AvgWattsChartComponent,
    SubDeviceChartComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule
  ],
  providers: [AvgWattsService, AppPersistenceService, SubDeviceService],
  bootstrap: [AppComponent]
})
export class AppModule { }
