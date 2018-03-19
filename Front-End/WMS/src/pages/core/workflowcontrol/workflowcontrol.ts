import { Component } from '@angular/core';
import { IonicPage, NavController } from 'ionic-angular';

/**
 * Generated class for the WorkflowcontrolPage tabs.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@IonicPage()
@Component({
  selector: 'page-workflowcontrol',
  templateUrl: 'workflowcontrol.html'
})
export class WorkflowcontrolPage {

  startWorkflowRoot = 'StartWorkflowPage'
  toDoListRoot = 'ToDoListPage'
  settingRoot = 'SettingPage'


  constructor(public navCtrl: NavController) {}

}
