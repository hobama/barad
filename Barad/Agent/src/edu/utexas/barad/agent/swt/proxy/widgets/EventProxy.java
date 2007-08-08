package edu.utexas.barad.agent.swt.proxy.widgets;

import edu.utexas.barad.agent.AgentUtils;
import edu.utexas.barad.agent.swt.proxy.SWTProxyFactory;
import edu.utexas.barad.agent.swt.proxy.SWTProxyMarker;
import edu.utexas.barad.agent.swt.proxy.graphics.RectangleProxy;

/**
 * University of Texas at Austin
 * Barad Project, Jul 7, 2007
 */
public interface EventProxy extends SWTProxyMarker {
    public int __fieldGetbutton();

    public void __fieldSetbutton(int button);

    public char __fieldGetcharacter();

    public void __fieldSetcharacter(char character);

    public int __fieldGetcount();

    public void __fieldSetcount(int count);

    public Object __fieldGetdata();

    public void __fieldSetdata(Object data);

    public int __fieldGetdetail();

    public void __fieldSetdetail(int detail);

    public DisplayProxy __fieldGetdisplay();

    public void __fieldSetdisplay(DisplayProxy display);

    public boolean __fieldGetdoit();

    public void __fieldSetdoit(boolean doit);

    public int __fieldGetend();

    public void __fieldSetend(int end);

    public int __fieldGetheight();

    public void __fieldSetheight(int height);

    public int __fieldGetindex();

    public void __fieldSetindex(int index);

    public WidgetProxy __fieldGetitem();

    public void __fieldSetitem(WidgetProxy item);

    public int __fieldGetkeyCode();

    public void __fieldSetkeyCode(int keycode);

    public int __fieldGetstart();

    public void __fieldSetstart(int start);

    public int __fieldGetstateMask();

    public void __fieldSetstateMask(int stateMask);

    public String __fieldGettext();

    public void __fieldSettext(String text);

    public int __fieldGettime();

    public void __fieldSettime(int time);

    public int __fieldGettype();

    public void __fieldSettype(int type);

    public WidgetProxy __fieldGetwidget();

    public void __fieldSetwidget(WidgetProxy widget);

    public int __fieldGetwidth();

    public void __fieldSetwidth(int width);

    public int __fieldGetx();

    public void __fieldSetx(int x);

    public int __fieldGety();

    public void __fieldSety(int y);

    public RectangleProxy getBounds();

    public static class Factory {
        public static EventProxy newEventProxy() {
            return (EventProxy) SWTProxyFactory.getInstance().newProxy(AgentUtils.swtClassForName(SWTProxyFactory.ORG_ECLIPSE_SWT_WIDGETS_EVENT), null, null);
        }
    }
}