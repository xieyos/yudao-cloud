package cn.iocoder.yudao.module.ai.framework.ai.core.model.chat;

import com.azure.ai.openai.OpenAIClientBuilder;
import com.azure.core.credential.AzureKeyCredential;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.ai.azure.openai.AzureOpenAiChatModel;
import org.springframework.ai.azure.openai.AzureOpenAiChatOptions;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.ai.model.azure.openai.autoconfigure.AzureOpenAiChatProperties.DEFAULT_DEPLOYMENT_NAME;

/**
 * {@link AzureOpenAiChatModel} 集成测试
 *
 * @author 芋道源码
 */
public class AzureOpenAIChatModelTests {

    // TODO @芋艿：晚点在调整
    private final OpenAIClientBuilder openAiApi = new OpenAIClientBuilder()
            .endpoint("https://eastusprejade.openai.azure.com")
            .credential(new AzureKeyCredential("xxx"));
    private final AzureOpenAiChatModel chatModel = AzureOpenAiChatModel.builder()
            .openAIClientBuilder(openAiApi)
            .defaultOptions(AzureOpenAiChatOptions.builder()
                    .deploymentName(DEFAULT_DEPLOYMENT_NAME)
                    .build())
            .build();

    @Test
    @Disabled
    public void testCall() {
        // 准备参数
        List<Message> messages = new ArrayList<>();
        messages.add(new SystemMessage("你是一个优质的文言文作者，用文言文描述着各城市的人文风景。"));
        messages.add(new UserMessage("1 + 1 = ？"));

        // 调用
        ChatResponse response = chatModel.call(new Prompt(messages));
        // 打印结果
        System.out.println(response);
        System.out.println(response.getResult().getOutput());
    }

    @Test
    @Disabled
    public void testStream() {
        // 准备参数
        List<Message> messages = new ArrayList<>();
        messages.add(new SystemMessage("你是一个优质的文言文作者，用文言文描述着各城市的人文风景。"));
        messages.add(new UserMessage("1 + 1 = ？"));

        // 调用
        Flux<ChatResponse> flux = chatModel.stream(new Prompt(messages));
        // 打印结果
        flux.doOnNext(response -> {
//            System.out.println(response);
            System.out.println(response.getResult().getOutput());
        }).then().block();
    }

}
