import { NgModule } from '@angular/core';
import { IonicPageModule } from 'ionic-angular';
import { QaissuePage } from './qaissue';

@NgModule({
  declarations: [
    QaissuePage,
  ],
  imports: [
    IonicPageModule.forChild(QaissuePage),
  ],
})
export class QaissuePageModule {}
