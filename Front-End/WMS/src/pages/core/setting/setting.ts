import { Component } from '@angular/core';
import { IonicPage, NavController, NavParams,AlertController } from 'ionic-angular';
import { AppConfig } from "../../../app/app.config";
/**
 * Generated class for the SettingPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@IonicPage()
@Component({
  selector: 'page-setting',
  templateUrl: 'setting.html',
})
export class SettingPage {
  factory:String
  warehouse:String
  language:String
  constructor(public navCtrl: NavController, public navParams: NavParams,private alertCtrl: AlertController) {
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad SettingPage');
  }

  save(){
    let config = new AppConfig();
    config.setFactoryCode(this.factory);
    config.setWarehouse(this.warehouse);
    config.setLanguage(this.language);
    let saveMsg = this.alertCtrl.create({
          title: "Setting",
          subTitle: "Save successfully!",
          buttons: [{
            text: 'OK',
            handler: () => {
              this.exitPage();
            }
          }
          ]
        });
        saveMsg.present();
  }
  exitPage() {
    console.log('Exit');
  }
}
