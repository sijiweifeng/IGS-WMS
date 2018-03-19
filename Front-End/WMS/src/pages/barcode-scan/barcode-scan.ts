import { Component } from '@angular/core';
import { IonicPage, NavController, NavParams } from 'ionic-angular';
import { BarcodeScanner } from '@ionic-native/barcode-scanner';


/**
 * Generated class for the BarcodeScanPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@IonicPage()
@Component({
  selector: 'page-barcode-scan',
  templateUrl: 'barcode-scan.html',
})
export class BarcodeScanPage {

  constructor(public navCtrl: NavController, public navParams: NavParams,private barcodeScanner: BarcodeScanner) {
  }


}
