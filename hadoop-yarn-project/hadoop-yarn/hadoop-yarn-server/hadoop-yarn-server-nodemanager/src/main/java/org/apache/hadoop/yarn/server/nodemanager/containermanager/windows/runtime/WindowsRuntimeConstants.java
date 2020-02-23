package org.apache.hadoop.yarn.server.nodemanager.containermanager.windows.runtime;

import org.apache.hadoop.yarn.server.nodemanager.containermanager.linux.runtime.DelegatingLinuxContainerRuntime;

public final class WindowsRuntimeConstants {
  private WindowsRuntimeConstants() {

  }

  /**
   * Linux container runtime types for {@link DelegatingLinuxContainerRuntime}.
   */
  public enum RuntimeType {
    DEFAULT
  }
}
