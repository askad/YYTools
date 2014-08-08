package yy.common;

import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.SimpleScriptContext;

public class JSTools {
	public static ScriptEngineManager mgr;
	public static ScriptEngine engine;
	public static ScriptContext newContext;
	public static Bindings bind;
	public Map<String, Object> map = new HashMap<String, Object>();

	public JSTools() {
		mgr = new ScriptEngineManager();
		engine = mgr.getEngineByName("javascript");
		newContext = new SimpleScriptContext();
		bind = newContext.getBindings(ScriptContext.ENGINE_SCOPE);
		
	}

	public void loadJSFile(String jsfile) throws Exception {
		bind.putAll(map);
		engine.setBindings(bind, ScriptContext.ENGINE_SCOPE);
		engine.eval(new FileReader(jsfile));
	}

	public void runJs(String jstx) throws Exception {
		engine.eval(jstx);
	}

	public void passPara(String name, String value) {
		map.put(name, value);
	}

	public Object getPara(String name) {
		return bind.get(name);
	}

	public static void main(String[] args) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		// 获取脚本引擎
		ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine engine = mgr.getEngineByName("javascript");
		// 绑定数据
		ScriptContext newContext = new SimpleScriptContext();
		Bindings bind = newContext.getBindings(ScriptContext.ENGINE_SCOPE);
		bind.putAll(map);
		map.put("pbk1", "10001");
		map.put("pass", "pass");
		engine.setBindings(bind, ScriptContext.ENGINE_SCOPE);
		engine.eval("var a=0;a=a+pass");
		System.out.print(bind.get("a"));

	}

}
