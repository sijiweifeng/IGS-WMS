import { Component } from '@angular/core';
import { IonicPage, NavController, NavParams } from 'ionic-angular';
import { ScanGarmentPage } from '../core/Receipt/Garment/scan-garment/scan-garment';
/**
* Generated class for the PrintPage page.
*
* See https://ionicframework.com/docs/components/#navigation for more info on
* Ionic pages and navigation.
*/

@IonicPage()
@Component({
  selector: 'page-print',
  templateUrl: 'print.html',
})
export class PrintPage {

  scanpage: any;

  constructor(public navCtrl: NavController, public navParams: NavParams) {
    this.scanpage = ScanGarmentPage;
  }

  print(){
    console.log("printing label");
    this.navCtrl.push(this.scanpage);
    //The function that finish the step a head and record percentage change
  }

  cancel(){
    this.navCtrl.pop();
  }


  ionViewDidLoad() {
    console.log('ionViewDidLoad PrintPage');
  }

}
