<template>
    <div class="chat-box">
        <div class="chat-box__content">
            <input
                style="border-radius: 15px;border: 1px solid grey"
                v-model="text"
                placeholder="Write a message..."
                type="text"
                @keyup.enter="onClickButtonDo"/>
            <BaseButton
                color="primary"
                iconRight="ri:send-plane-fill"
                iconSize="20px"
                size="medium"
                rounded
                :disabled="isButtonDisabled"
                @click="onClickButtonDo">
                Send
            </BaseButton>
        </div>
    </div>
</template>

<script>
import { ref, computed } from 'vue';
import BaseButton from '@/components/base/BaseButton.vue';

export default {
  name: 'ChatBox',
  components: {
    BaseButton
  },
  setup( props, content ) {
    const text = ref( '' );
    const isButtonDisabled = computed( () => {
      return text.value === '';
    } );

    const onClickButtonDo = () => {
      content.emit( 'update:messages', text.value );
      text.value = '';
    };

    return {
      text,
      isButtonDisabled,
      onClickButtonDo
    };
  }
};
</script>

<style  scoped>
.chat-box {
  display: flex;
  width: 94%;
  padding: 15px;
}
.chat-box .chat-box__content {
  display: flex;
  position: relative;
  height: 60px;
  width: 100%;
  background: white;
  border-radius: 15px;
}
.chat-box .chat-box__content input {
  display: flex;
  padding: 0 100px 0 15px;
  background: transparent;
  font-size: 14px;
  flex-grow: 1;
  outline: none;
}
.chat-box .chat-box__content .base-button {
  position: absolute;
  top: 50%;
  right: 5px;
  transform: translateY(-50%);
}

/*# sourceMappingURL=test.css.map */

</style>