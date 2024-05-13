import asyncio
import websockets
from openai import OpenAI

# ANSI转义序列
RED = '\033[91m'
GREEN = '\033[92m'
YELLOW = '\033[93m'
BLUE = '\033[94m'
MAGENTA = '\033[95m'
CYAN = '\033[96m'
WHITE = '\033[97m'
RESET = '\033[0m'

client = OpenAI(
    api_key="sk-cYl7mq9JrnnzbUYb8Vd6b9PaL1ya5inPbc3Eza7jOz74YsSB",
    base_url="https://api.moonshot.cn/v1",
)
originMsg = {
    "role": "system",
    "content": "你是一个邮件助手，根据用户的要求总结邮件或者生成新邮件"
               "除非用户要求其他语言，否则输出中文"
}
firstMessages = [originMsg]
# file_object = client.files.create(file=Path("web.py"), purpose="file-extract")
# file_content = client.files.content(file_id=file_object.id).text

model = 'moonshot-v1-32k'
messages = firstMessages
stream = True
index = 0


async def send(websocket, msg):
    global messages
    if msg == "clean":
        messages = firstMessages
        return
    messages.append({"role": "user", "content": msg})
    response = client.chat.completions.create(
        model=model,
        messages=messages,
        temperature=1,
        stream=stream,
    )
    res = ""
    tempText = ""
    for idx, chunk in enumerate(response):
        chunk_message = chunk.choices[0].delta
        if not chunk_message.content:
            continue
        tempText += chunk_message.content
        if len(tempText) >= 50:
            await websocket.send(f"{tempText}")
            print(tempText)
            tempText = ""
        res += chunk_message.content
    if len(tempText) >= 0:
        await websocket.send(f"{tempText}")
        print(tempText)
    messages.append({"role": "assistant", "content": res})


async def echo(websocket, path):
    print("客户端已连接")

    try:
        async for message in websocket:
            print(f"收到消息：{message}")
            await send(websocket, message)

    except websockets.exceptions.ConnectionClosed as e:
        print(f"客户端异常断开：{e}")


async def main():
    async with websockets.serve(echo, "localhost", 10089):
        print("服务器启动，端口10089")
        await asyncio.Future()  # 运行服务器直到被停止

                



if __name__ == "__main__":
    asyncio.run(main())
