import { NgModule } from '@angular/core';
import { IonicPageModule } from 'ionic-angular';
import { PrintPage } from './print';
// import { ProgressBarComponent} from '../component/progress-bar/progress-bar';

@NgModule({
  declarations: [
    PrintPage,
  ],
  imports: [
    IonicPageModule.forChild(PrintPage),
  ],
})
export class PrintPageModule {}
