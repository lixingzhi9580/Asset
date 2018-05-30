package com.cn.iso8583;

import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

@Component
@ManagedResource
public class Iso8583Context {
	static final AtomicBoolean VERBOSE = new AtomicBoolean(false);
	
	@ManagedAttribute
	public boolean isVerbose() {
		return VERBOSE.get();
	}

	@ManagedAttribute
	public void setVerbose(boolean verbose) {
		Iso8583Context.VERBOSE.set(verbose);
	}
}
