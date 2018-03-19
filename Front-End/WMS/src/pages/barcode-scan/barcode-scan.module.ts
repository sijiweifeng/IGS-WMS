import { NgModule } from '@angular/core';
import { IonicPageModule } from 'ionic-angular';
import { BarcodeScanPage } from './barcode-scan';

@NgModule({
  declarations: [
    BarcodeScanPage,
  ],
  imports: [
    IonicPageModule.forChild(BarcodeScanPage),
  ],
})
export class BarcodeScanPageModule {}
