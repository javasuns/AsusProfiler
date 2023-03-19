/*
 * Copyright (C) 2022-2023 AsusProfiler 
 * Giannos Hadjipanayis
 * All rights reserved.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package javasuns.profiler.asus.model.scheduler;

import java.io.IOException;

import javasuns.profiler.asus.model.CmdManager;
import javasuns.profiler.asus.model.PropertyManager;

public class TaskScheduler {
	private static TaskScheduler taskScheduler;
	private static String exe = "schtasks.exe";
	private static String taskName = "AsusProfiler";
	
	public static TaskScheduler getInst() {
		if(taskScheduler == null)
			taskScheduler = new TaskScheduler();
		return taskScheduler;
	}

	public void addWindowsTask() throws IOException, InterruptedException {
		String taskExe  = PropertyManager.getAppExecutablePath();
		String taskArgs = "--startOnTray";
		String cmd = "%s /create /sc ONLOGON /tn \"%s\" /tr \"'%s' %s\" /RL HIGHEST".formatted(exe,taskName,taskExe,taskArgs);	
		String out = CmdManager.getInst().run(cmd);
		System.out.println("Output: " + out);
	} // addWindowsTask()
	
	public void deleteWindowsTask() throws IOException, InterruptedException {
		String cmd = "%s /delete /tn \"%s\" /f".formatted(exe,taskName);	
		String out = CmdManager.getInst().run(cmd);
		System.out.println("Output: " + out);
	} // deleteWindowsTask()
	
	public boolean isWindowsTaskActive()  {
		try {
			String cmd = "%s /query /tn \"%s\"".formatted(exe,taskName);	
			String out = CmdManager.getInst().run(cmd);

			return out.contains(taskName);
		} catch(Exception e) { e.printStackTrace(); }
		return false;
	} // isWindowsTaskActive()
} // class TaskScheduler
