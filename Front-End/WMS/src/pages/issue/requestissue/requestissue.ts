import { Component } from '@angular/core';
import { IonicPage, NavController, NavParams } from 'ionic-angular';
import {QaissuePage} from '../qaissue/qaissue';
import {FnissuePage} from '../fnissue/fnissue';
import {TranslateService} from '@ngx-translate/core';
/**
 * Generated class for the RequestissuePage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@IonicPage()
@Component({
  selector: 'page-requestissue',
  templateUrl: 'requestissue.html',
})
export class RequestissuePage {

    pushPage:any;
    dataSet: any = [];
    recordCustomer:any=[];
    recordStore:any=[];



    constructor(public navCtrl: NavController, public navParams: NavParams,public translate: TranslateService)
      {
        this.recordCustomer = [
          {name:"Calvin Kelin"},
          {name:"Tommy Heighler"},
          {name:"Zara"}
        ];

        this.recordStore=[
          {name:"StoreA"},
          {name:"StoreB"},
          {name:"StoreC"}
        ];

        translate.use('en');
        translate.setTranslation('zh-tw',
        {
          "Trans Date":"開始日期",
          "To": "至",
          "View Past Record": "瀏覽過往紀錄",
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
          Query: '查取',
          "Record Table":"紀錄",
          "Trans Code": "號碼",
          "Doc No": "文件號碼",
          "Created By":"創建者",
          "Status":"狀態"
        }
      );

        this.dataSet =[
          {
            type:"QA",
            store: "StoreA",
            trans: "2018-03-13",
            transCode: "435453",
            docNo: "e5345",
            creator: "sdfsdf",
            status: "Active",
            JO: "Jo342",
            cartons: "45",
            Qty: "45"
          },
          {
            type:"Active",
            store: "StoreA",
            trans: "2018-03-13",
            transCode: "435453",
            docNo: "e5345",
            creator: "sdfsdf",
            status: "Active",
            JO: "Jo342",
            cartons: "45",
            Qty: "45"
          }
        ];
      }

      changeLanguage(lang){
        this.translate.use(lang);
      }

      determinePushPage(item:any){
        if(item.type== 'QA' )
        {
          this.pushPage = QaissuePage;
        }

        else if(item.type == "Active"){
          this.pushPage = FnissuePage;
        }
        this.navCtrl.push(this.pushPage);
      }

      ionViewDidLoad() {
        console.log('ionViewDidLoad ViewrecordPage');
      }

    }
