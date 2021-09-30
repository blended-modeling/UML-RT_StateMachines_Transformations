/*******************************************************************************
 * Copyright (c) 2014-2016 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.codegen.build;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.uml2.common.util.UML2Util;


/**
 * UMLRT source-gen builder notifier.
 * 
 * @author ysroh
 *
 */
public class BuildNotifier {

	/**
	 * progress monitor.
	 */
	protected IProgressMonitor monitor;
	/**
	 * 
	 */
	protected boolean cancelling;
	/**
	 * 
	 */
	protected float percentComplete;
	/**
	 * 
	 */
	protected int workDone;
	/**
	 * 
	 */
	protected int totalWork;
	/**
	 * 
	 */
	protected String previousSubtask;

	/**
	 * 
	 * Constructor.
	 *
	 * @param monitor
	 *            monitor
	 * @param project
	 *            project
	 */
	public BuildNotifier(IProgressMonitor monitor, IProject project) {
		this.monitor = monitor;
		this.cancelling = false;
		this.workDone = 0;
		this.totalWork = 1000000;
	}

	/**
	 * Begin task.
	 */
	public void begin() {
		if (this.monitor != null) {
			this.monitor.beginTask(UML2Util.EMPTY_STRING, this.totalWork);
		}
		this.previousSubtask = null;
	}

	/**
	 * Check whether the build has been canceled.
	 */
	public void checkCancel() {
		if (this.monitor != null && this.monitor.isCanceled()) {
			throw new OperationCanceledException();
		}
	}

	public void setCancelling(boolean cancelling) {
		this.cancelling = cancelling;
	}

	/**
	 * Done.
	 */
	public void done() {
		updateProgress(1.0f);
		subTask("Project synchronization complete");
		if (this.monitor != null) {
			this.monitor.done();
		}
		this.previousSubtask = null;
	}

	/**
	 * Sub task.
	 * 
	 * @param msg
	 *            message
	 */
	public void subTask(String msg) {
		if (msg.equals(this.previousSubtask)) {
			return;
		}
		if (this.monitor != null) {
			this.monitor.subTask(msg);
		}
		this.previousSubtask = msg;
	}

	/**
	 * Update progress.
	 * 
	 * @param newPercentComplete
	 *            complete percentage
	 */
	public void updateProgress(float newPercentComplete) {
		if (newPercentComplete > this.percentComplete) {
			this.percentComplete = Math.min(newPercentComplete, 1.0f);
			int work = Math.round(this.percentComplete * this.totalWork);
			if (work > this.workDone) {
				if (this.monitor != null) {
					this.monitor.worked(work - this.workDone);
				}
				this.workDone = work;
			}
		}
	}
}
