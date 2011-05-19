/*
g = GlobalsGui.new;
a = 12;
z = 8768768;
q = (a: 123, b: 234);

t = g.texts.first.textField;
t.action = { thisProcess.interpreter.perform(\a_, 123) }

// todo: 

*	compare with prevState, only update if needed
* 	maybe a smaller version that scrolls? 


*/

GlobalsGui : JITGui { 
	var <texts; 
	classvar <names = #[
		\a, \b, \c, \d, \e, \f, \g, 
		\h, \n, \j, \k, \l, \m, \n,
		\o, \p, \q, \r, \s, \t, \u, 
		\v, \w, \x, \y, \z, \cmdLine ]; 
	
	*new { |parent, bounds| 
		^super.new(thisProcess.interpreter, 0, parent, bounds);
	}
	
		// these methods should be overridden in subclasses:
	setDefaults { |options|
		if (parent.isNil) {
			defPos = 10@260
		} {
			defPos = skin.margin;
		};
		minSize = 200 @ (names.size * skin.buttonHeight + 10);
	}
	
	makeViews { 
		
		texts = names.collect { |name, i| 
			var labelWidth = 15, canEval = true; 
			var text;
			if (name == 'cmdLine', { 
				labelWidth = 60;
				canEval = false; 
			});
			
			text = EZText(zone, 188@ skin.buttonHeight, name, labelWidth: labelWidth);
			text.view.resize_(2);
			text.labelView.align_(\center); 
			text.enabled_(canEval);
			text; 
		};
		this.name_(this.getName);
	}
	
	getState { 
		var state = ();
		names.do { |globvar| 
			state.put(globvar, object.instVarAt(globvar))
		};
		^state;
	}
	
	getName { ^"Global_Vars" }
	winName { ^this.getName }
				
	checkUpdate { 
		var newState = this.getState; 
		names.do { |globvar, i|
			var obj = newState[globvar];
			if (obj != prevState[globvar]) { 
				texts[i].value_(obj);
			};
		};
		// texts.last.textField = texts.last.textField
		prevState = newState;
	}
}