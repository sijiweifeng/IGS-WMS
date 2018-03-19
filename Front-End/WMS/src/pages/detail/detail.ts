import { Component } from '@angular/core';
import { NavParams, ViewController ,NavController} from 'ionic-angular';

@Component({
  selector: 'page-detail',
  templateUrl: 'detail.html',
  // providers:[NavController]
})
export class DetailPage {
  myParam: string;

  constructor(
    public viewCtrl: ViewController,
    params: NavParams
  )
  {
    this.myParam = params.get('myParam');
  }

  dismiss() {
    this.viewCtrl.dismiss();
  }
}
