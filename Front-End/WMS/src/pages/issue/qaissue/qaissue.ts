import { Component } from '@angular/core';
import { NavController } from 'ionic-angular';
import { BarcodeScanner } from '@ionic-native/barcode-scanner';
import { WorkflowcontrolPage } from '../../core/workflowcontrol/workflowcontrol';
import { Toast } from '@ionic-native/toast';
import {TranslateService} from '@ngx-translate/core';
/**
 * Generated class for the QaissuePage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@IonicPage()
@Component({
  selector: 'page-qaissue',
  templateUrl: 'qaissue.html',
})
export class QaissuePage {
  param = {value: 'world'};
 products: any[] = [];
 selectedProduct: any;
 pushPage:any;
 productFound:boolean = false;


   constructor(
     public navCtrl: NavController,
     private barcodeScanner: BarcodeScanner,
     private toast: Toast,
     translate:TranslateService)

     {
       this.pushPage = WorkflowcontrolPage;

       translate.setTranslation('en', {
         HELLO: '中文'
       });
       translate.use('en');

     }
     scan() {
       this.barcodeScanner.scan().then((barcodeData) => {
         console.log(barcodeData);
       }, (err) => {
         this.toast.show(err, '5000', 'center').subscribe(
           toast => {
             console.log(toast);
           }
         );
       });
     }

   }
