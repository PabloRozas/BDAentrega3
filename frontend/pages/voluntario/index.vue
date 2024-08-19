<template>
  <div>
    <input
      v-model="message"
      @keyup.enter="sendMessage"
      placeholder="Enter message"
    />
    <div v-for="msg in messages" :key="msg.id">{{ msg.content }}</div>
  </div>
</template>

<script lang="ts" setup>
import WebSocketService from "@/services/WebSocketService.js";
import { ref, onMounted, onBeforeUnmount } from "vue";

const auth = useAuthStore();

const message = ref("");
const messages = ref<Array<{ id: number; content: string }>>([]);
const token = auth.getToken();
const webSocketService = new WebSocketService(token);

onMounted(() => {
  webSocketService.connect((msg: { id: number; content: string }) => {
    messages.value.push(msg);
  });
});

const sendMessage = () => {
  const newMessage = {
    content: message.value,
    id: Date.now(), // Generar un ID único para el mensaje enviado
  };
  webSocketService.sendMessage(newMessage);
  message.value = "";
};

onBeforeUnmount(() => {
  webSocketService.disconnect();
});
</script>
