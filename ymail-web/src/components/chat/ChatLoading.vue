<template>
    <div class="chat-loading">
        <div class="chat-loading__avatar">
<!--            <AssistantLogoImage />-->
        </div>
        <div class="chat-loading__content">
            <div></div>
            <div></div>
            <div></div>
        </div>
    </div>
</template>

<script>
// import AssistantLogoImage from '@/assets/assistant_logo.svg';

export default {
  name: 'ChatLoading',
  components: {
    // AssistantLogoImage
  }
};
</script>

<style scoped>
.chat-loading {
  display: flex;
  margin-top: 10px;
}
.chat-loading .chat-loading__avatar {
  width: 30px;
  margin-right: 5px;
  border-radius: 100%;
  overflow: hidden;
  flex-shrink: 0;
}
.chat-loading .chat-loading__avatar svg {
  width: 30px;
}
.chat-loading .chat-loading__content {
  display: flex;
  height: 30px;
  padding: 10px 10px 15px;
  background: var(--color--gray-lighter);
  align-items: center;
  justify-content: center;
  border-radius: 15px;
  gap: 8px;
}
.chat-loading .chat-loading__content > div {
  height: 10px;
  width: 10px;
  background: rgba(0, 0, 0, 0.3);
  border-radius: 50%;
  animation: cycle 1s ease-in-out infinite;
}
.chat-loading .chat-loading__content > div:nth-child(1) {
  animation-delay: 0;
}
.chat-loading .chat-loading__content > div:nth-child(2) {
  animation-delay: 0.2s;
}
.chat-loading .chat-loading__content > div:nth-child(3) {
  animation-delay: 0.4s;
}

@keyframes cycle {
  0% {
    transform: translateY(0);
  }
  50% {
    transform: translateY(5px);
  }
  100% {
    transform: translateY(0);
  }
}

/*# sourceMappingURL=test.css.map */

</style>