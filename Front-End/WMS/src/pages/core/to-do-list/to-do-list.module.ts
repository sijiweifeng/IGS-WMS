import { NgModule } from '@angular/core';
import { IonicPageModule } from 'ionic-angular';
import { ToDoListPage } from './to-do-list';
import { NgCircleProgressModule } from 'ng-circle-progress';
import {PipesModule} from '../../../pipes/pipes.module';

@NgModule({
  declarations: [
    ToDoListPage,
  ],
  imports: [
    PipesModule,
    IonicPageModule.forChild(ToDoListPage),
    NgCircleProgressModule.forRoot
    (
      {
        radius: 100,
        outerStrokeWidth: 16,
        innerStrokeWidth: 8,
        outerStrokeColor: "linear-gradient(#C7E596, #fff)",
        innerStrokeColor: "#C7E596",
        animationDuration: 300,
      }
    )
  ],
  providers: []
})
export class ToDoListPageModule {}
