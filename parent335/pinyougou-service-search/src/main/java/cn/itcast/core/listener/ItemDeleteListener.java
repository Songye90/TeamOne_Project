package cn.itcast.core.listener;

import cn.itcast.core.service.search.ItemSearchService;
import org.apache.activemq.command.ActiveMQTextMessage;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * 自定义消息监听器：商品下架
 */
public class ItemDeleteListener implements MessageListener {

    @Resource
    private ItemSearchService itemSearchService;

    /**
     * 完成具体的业务
     * @param message
     */
    @Override
    public void onMessage(Message message) {

        try {
            // 取出消息
            ActiveMQTextMessage activeMQTextMessage = (ActiveMQTextMessage) message;
            String id = activeMQTextMessage.getText();
            // 消费消息
            System.out.println("删除索引库："+id);
            itemSearchService.deleteItemFromSolr(Long.parseLong(id));
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
