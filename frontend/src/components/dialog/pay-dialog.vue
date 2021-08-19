<template>
  <div class="modal-mask" v-if="state.dialogVisible">
    <div class="modal-container" @keyup.esc="handleClose" style="width: 400px; height: 480px;">
      <div class="modal-header">
        결제하기
        <svg
          class="btn-modal-close"
          fill="none"
          stroke="currentColor"
          viewBox="0 0 24 24"
          xmlns="http://www.w3.org/2000/svg"
          @click="handleClose"
        >
        <path
          stroke-linecap="round"
          stroke-linejoin="round"
          stroke-width="2"
          d="M6 18L18 6M6 6l12 12"
          ></path>
        </svg>
      </div>
      <div class="modal-body">
        <p class="modal-box modal-box-label">
          클래스
        </p>
        <p class="modal-box modal-box-text">
          {{ itemName }}
        </p>
        <p class="modal-box modal-box-label">
          가격
        </p>
        <p class="modal-box modal-box-text">
          {{ price }}
        </p>
        <p class="modal-box modal-box-label">
          구매자 아이디
        </p>
        <p class="modal-box modal-box-text">
          {{ userId }}
        </p>
        <button class="btn btn-submit modal-box" @click=onClickPay>결제하기</button>
      </div>
    </div>
  </div>
</template>

<script>
import { reactive, computed, onMounted } from 'vue'
import BootPay from 'bootpay-js'
import { useStore } from 'vuex'

export default {
  name: 'pay-dialog',
  props: {
    open: {
      type: Boolean,
      default: false
    },
    price: {
      type: Number
    },
    userId: {
      type: String
    },
    itemName: {
      type: String
    },
    conferenceId: {
      type: String
    }
  },
  setup(props, {emit}) {
    const store = useStore()
    const state = reactive({
      dialogVisible: computed(() => props.open),
    })

    onMounted(() => {

    })

    const handleClose = function () {
      emit('closePayDialog')
    }

    const onClickPay = function () {
      console.log(props.conferenceId)
      BootPay.request({
      // price: props.price.toString(),
      price: props.price,
      application_id: '611b54ed7b5ba4001d52a23c',
      name: props.itemName, //결제창에서 보여질 이름
      pg: 'inicis',
      method: 'card', //결제수단, 입력하지 않으면 결제수단 선택부터 화면이 시작합니다.
      show_agree_window: 0, // 부트페이 정보 동의 창 보이기 여부
      items: [
          {
            item_name: props.itemName, //상품명 ****
            qty: 1, //수량
            // GET 요청으로 구매 번호 (order index)를 받아와야 함
            unique: props.conferenceId, //해당 상품을 구분짓는 primary key
            price: props.price, //상품 단가 ****
          }
      ],
      order_id: props.conferenceId, //고유 주문번호로, 생성하신 값을 보내주셔야 합니다. ****
      }).error(function (data) {
        //결제 진행시 에러가 발생하면 수행됩니다.
        // location.replace('pay/delete?id='+ '1'); //DB 값 삭제
      }).ready(function (data) {
        // 가상계좌 입금 계좌번호가 발급되면 호출되는 함수입니다.
      }).confirm(function (data) {
        //결제가 실행되기 전에 수행되며, 주로 재고를 확인하는 로직이 들어갑니다.
        //주의 - 카드 수기결제일 경우 이 부분이 실행되지 않습니다.
        var enable = true; // 재고 수량 관리 로직 혹은 다른 처리
        if (enable) {
            BootPay.transactionConfirm(data); // 조건이 맞으면 승인 처리를 한다.
        } else {
            BootPay.removePaymentWindow(); // 조건이 맞지 않으면 결제 창을 닫고 결제를 승인하지 않는다.
        }
      }).cancel(function (data) {
        //결제가 취소되면 수행됩니다.
        // location.replace('pay/delete?id='+'1');    //DB 값 삭제
      }).close(function (data) {
        // 결제창이 닫힐때 수행됩니다. (성공,실패,취소에 상관없이 모두 수행됨)
      }).done(function (data) {
        //결제가 정상적으로 완료되면 수행됩니다
        //비즈니스 로직을 수행하기 전에 결제 유효성 검증을 하시길 추천합니다.
        store.dispatch('root/requestConferenceJoin', props.conferenceId)
        .then(() => {
          alert('클래스 수강신청에 성공하셨습니다!')
          emit('closePayDialog')
          window.location.reload()
        })
        .catch((err) => console.log(err))
      });
    }

    return { state, handleClose, onClickPay }
  }
}
</script>

<style>

</style>
