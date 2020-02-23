package org.apache.hadoop.yarn.server.nodemanager.containermanager.windows.runtime;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.yarn.server.nodemanager.Context;
import org.apache.hadoop.yarn.server.nodemanager.containermanager.runtime.ContainerExecutionException;
import org.apache.hadoop.yarn.server.nodemanager.containermanager.runtime.ContainerRuntime;

import java.util.Map;

/**
 * Defined as separate to provide extendability for Windows Runtime
 */
public interface WindowsContainerRuntime extends ContainerRuntime {
  /**
   * Initialize the runtime.
   *
   * @param conf      the {@link Configuration} to use
   * @param nmContext NMContext
   * @throws ContainerExecutionException if an error occurs while initializing
   *                                     the runtime
   */
  void initialize(Configuration conf, Context nmContext)
      throws ContainerExecutionException;

  /**
   * Return whether the given environment variables indicate that the operation
   * is requesting this runtime.
   *
   * @param env the environment variable settings for the operation
   * @return whether this runtime is requested
   */
  boolean isRuntimeRequested(Map<String, String> env);
}
