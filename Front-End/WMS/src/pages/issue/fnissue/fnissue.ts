import { IonicPage, NavController, NavParams } from 'ionic-angular';
import { Component } from '@angular/core';
import {TranslateService} from '@ngx-translate/core';

/**
* Generated class for the FnissuePage page.
*
* See https://ionicframework.com/docs/components/#navigation for more info on
* Ionic pages and navigation.
*/

@IonicPage()
@Component({
  selector: 'page-fnissue',
  templateUrl: 'fnissue.html',
})
export class FnissuePage {
  selectedItem: any;
  icons: string[];
  activeCustomer:any=[];
  activeStore:any=[];

  constructor(public navCtrl: NavController, public navParams: NavParams,
    public translate: TranslateService)
    {
      this.activeCustomer = [
        {name:"Calvin Kelin"},
        {name:"Tommy Heighler"},
        {name:"Zara"}
      ];

      this.activeStore=[
        {name:"StoreA"},
        {name:"StoreB"},
        {name:"StoreC"}
      ];


      translate.use('en');
      translate.setTranslation('zh-tw',
      {
        "Active Issue":'出倉申請',
        QA: '客查申请',
        Store: '店舖',
        Check: '已查收',
        "Check Table": '查收表',
        Customer: '顧客',
        Name: '名稱',
        Quantity: '數量',
        "Carton No": "箱數量",
        Style : '風格',
        "Style Rev": '風格參考',
        "Style Description": '風格描述',
        Buyer: '買家',
        Close: '關閉',
        Confirm:'確定',
        Reset: '重設',
        Import: '匯入',
        Delete: '刪除',
        Query: '查取'
      }
    );
  }

  changeLanguage(lang){
    this.translate.use(lang);
  }

}
