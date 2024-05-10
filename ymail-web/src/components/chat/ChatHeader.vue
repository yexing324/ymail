<template>
    <div class="chat-header">
        <div class="chat-header__content">
            <Icon class="mr-2" icon="mdi:chat-outline" width="25px" height="25px" />
            <span>邮件ai助手</span>
        </div>
        <div class="chat-header__details">
            <span>请注意：你正在和Ai聊天</span>
        </div>
    </div>
</template>

<script>
import { Icon } from '@iconify/vue';

export default {
  name: 'ChatHeader',
  components: {
    Icon
  }
};
</script>

<style  scoped>
.chat-header {
  display: flex;
  width: 100%;
  align-items: center;
  flex-direction: column;
}
.chat-header .chat-header__content {
  display: flex;
  width: 94%;
  padding: 15px;
  background: #29688d;
  color: white;
  flex-direction: row;
  border-top-left-radius: 10px;
  border-top-right-radius: 10px;
}
.chat-header .chat-header__details {
  width: 94%;
  padding: 10px 15px;
  color: grey;
  font-size: 14px;
  border-bottom: 1px solid grey;
}
.chat-header .chat-header__details b {
  color: black;
}

/*# sourceMappingURL=test.css.map */

</style>